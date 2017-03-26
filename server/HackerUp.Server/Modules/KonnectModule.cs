
using System;
using System.Collections.Generic;
using System.Linq;
using GeoCoordinatePortable;
using HackerUp.Server.Configuration;
using HackerUp.Server.DataModels;
using HackerUp.Server.Events;
using HackerUp.Server.Services.Auth;
using Nancy;
using Nancy.ModelBinding;
using Nancy.Security;
using Octokit;
using OsmiumSubstrate.Utilities;

namespace HackerUp.Server.Modules
{
    public class KonnectModule : NancyModule
    {
        public IHUServerContext ServerContext { get; set; }
        public UserManagerService UserManager { get; set; }
        public KonnectModule(IHUServerContext serverContext) : base("/a/k")
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

            Get("/nearby/{dist}", args =>
            {
                try
                {
                    double distanceRange = (double)args.dist;
                    // get current user
                    var connUser = ServerContext.ConnectedUsers.Find(x => x.DbUser.ApiKey == apiKey);
                    if (connUser == null) return HttpStatusCode.BadRequest;
                    // var nearbyUsers = ServerContext.ConnectedUsers.FindAll(x => x != connUser && x.LastLocation != null && connUser.LastLocation.GetDistanceTo(x.LastLocation) < distanceRange);
                    var nearbyUsers = new List<ConnectedUser>();
                    foreach (var u in ServerContext.ConnectedUsers)
                    {
                        if (u != connUser && u.LastLocation != null)
                        {
                            var uDist = connUser.LastLocation.GetDistanceTo(u.LastLocation);
                            if (uDist < distanceRange)
                            {
                                nearbyUsers.Add(u);
                            }
                        }
                    }
                    return Response.AsJsonNet(nearbyUsers.Select(x => new NearbyUser
                    {
                        Distance = connUser.LastLocation.GetDistanceTo(x.LastLocation),
                        UserId = x.DbUser.PublicUserId,
                        Name = x.DbUser.FullName
                    }));
                }
                catch
                {
                    return HttpStatusCode.BadRequest;
                }
            });

            Get("/profile/{publicId}", async args =>
            {
                try
                {
                    var publicId = (string)args.publicId;
                    if (publicId == null) return HttpStatusCode.BadRequest;
                    var selectedUser = UserManager.FindUserByPublicId(publicId);
                    if (selectedUser == null) return HttpStatusCode.NotFound;
                    var ghClient = new GitHubClient(new ProductHeaderValue(nameof(HUAuthenticationModule)));
                    var githubUser = await ghClient.User.Get(selectedUser.GitHubUsername);

                    var profile = new UserProfile
                    {
                        RepoCount = githubUser.PublicRepos,
                        FullName = selectedUser.FullName,
                        HangoutsEmail = selectedUser.HangoutsEmail,
                        GitHubUsername = selectedUser.GitHubUsername,
                        GitHubBio = githubUser.Bio,
                        Company = githubUser.Company,
                        HomeLocation = githubUser.Location
                    };
                    return Response.AsJsonNet(profile);
                }
                catch
                {
                    return HttpStatusCode.BadRequest;
                }
            });
        }
    }
}