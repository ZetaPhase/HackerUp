
using System;
using HackerUp.Server.Configuration;
using HackerUp.Server.DataModels;
using HackerUp.Server.Events;
using HackerUp.Server.Services.Auth;
using Nancy;
using Nancy.ModelBinding;
using Nancy.Security;

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
            var apiKey = (string)Context.Request.Query.apikey.Value;
            UserManager = new UserManagerService(ServerContext);
            var user = UserManager.FindUserByApiKey(apiKey);

            Post("/ping", args =>
            {
                try
                {
                    var pingReq = this.Bind<PingRequest>();
                    // TODO: store ping
                    var connUser = ServerContext.ConnectedUsers.Find(x => x.DbUser.ApiKey == apiKey);
                    if (connUser == null)
                    {
                        connUser = new ConnectedUser(user);
                        ServerContext.ConnectedUsers.Add(connUser);
                    }
                    else
                    {
                        connUser.Ping(DateTime.Now);
                    }
                    return HttpStatusCode.OK;
                }
                catch
                {
                    return HttpStatusCode.BadRequest;
                }
            });
        }
    }
}