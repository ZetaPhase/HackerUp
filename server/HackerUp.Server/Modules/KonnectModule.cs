
using HackerUp.Server.DataModels;
using HackerUp.Server.Services.Auth;
using Nancy;
using Nancy.ModelBinding;
using Nancy.Security;

namespace HackerUp.Server.Modules
{
    public class KonnectModule : NancyModule
    {
        public KonnectModule() : base("/k")
        {
            this.RequiresClaims(x => x.Value == UserApiLoginValidator.StatelessAuthClaim.Value);

            Post("/ping", args =>
            {
                try
                {
                    var pingReq = this.Bind<PingRequest>();
                    // TODO: store ping
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