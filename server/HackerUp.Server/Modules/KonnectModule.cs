
using System;
using System.Linq;
using GeoCoordinatePortable;
using HackerUp.Server.Configuration;
using HackerUp.Server.DataModels;
using HackerUp.Server.Events;
using HackerUp.Server.Services.Auth;
using Nancy;
using Nancy.ModelBinding;
using Nancy.Security;
using OsmiumSubstrate.Utilities;

namespace HackerUp.Server.Modules
{
    public class KonnectModule : NancyModule
    {
        public IHUServerContext ServerContext { get; set; }
        public UserManagerService UserManager { get; set; }
        public KonnectModule(IHUServerContext serverContext) : base("/k")
        {
            ServerContext = serverContext;
            
            this.RequiresClaims(x => x.Value == UserApiLoginValidator.StatelessAuthClaim.Value);
            RegisteredUser user = null;
            string apiKey = null;

            Before += (ctx) =>
            {
                apiKey = (string)Context?.Request.Query.apikey;
                if (apiKey != null)
                {
                    UserManager = new UserManagerService(ServerContext);
                    user = UserManager.FindUserByApiKey(apiKey);
                }
                return null;
            };

            Post("/ping", args =>
            {
                try
                {
                    var pingReq = this.Bind<PingRequest>();
                    var connUser = ServerContext.ConnectedUsers.Find(x => x.DbUser.ApiKey == apiKey);
                    var loc = new GeoCoordinate(pingReq.Latitude, pingReq.Longitude);
                    if (connUser == null)
                    {
                        connUser = new ConnectedUser(user);
                        connUser.Ping(loc);
                        ServerContext.ConnectedUsers.Add(connUser);
                    }
                    else
                    {
                        connUser.Ping(loc);
                    }
                    return HttpStatusCode.OK;
                }
                catch
                {
                    return HttpStatusCode.BadRequest;
                }
            });

            Get("/nearby", args => 
            {
                // get current user
                var connUser = ServerContext.ConnectedUsers.Find(x => x.DbUser.ApiKey == apiKey);
                if (connUser == null) return HttpStatusCode.BadRequest;
                var nearbyUsers = ServerContext.ConnectedUsers.FindAll(x => x != connUser && x.LastLocation != null && connUser.LastLocation.GetDistanceTo(x.LastLocation) < 1500);
                return Response.AsJsonNet(nearbyUsers.Select(x => new NearbyUser
                {
                    Distance = connUser.LastLocation.GetDistanceTo(x.LastLocation),
                    UserId = x.DbUser.PublicUserId
                }));
            });
            
        }
    }
}