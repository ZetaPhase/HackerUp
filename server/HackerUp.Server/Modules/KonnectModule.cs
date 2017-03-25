
using HackerUp.Server.Services.Auth;
using Nancy;
using Nancy.Security;

namespace HackerUp.Server.Modules
{
    public class KonnectModule : NancyModule
    {
        public KonnectModule() : base("/k")
        {
            this.RequiresClaims(x => x.Value == UserApiLoginValidator.StatelessAuthClaim.Value);

            
        }
    }
}