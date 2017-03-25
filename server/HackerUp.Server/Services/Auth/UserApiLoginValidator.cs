
using System.Security.Claims;
using System.Security.Principal;
using HackerUp.Server.Configuration;

namespace HackerUp.Server.Services.Auth
{
    public class UserApiLoginValidator
    {
        public IHUServerContext ServerContext { get; }

        public static Claim StatelessAuthClaim { get; } = new Claim("authType", "stateless");

        public const string UserApiKeyClaim = "userkey";

        public UserManagerService UserManager { get; }

        public UserApiLoginValidator(IHUServerContext serverContext)
        {
            ServerContext = serverContext;
            UserManager = new UserManagerService(ServerContext);
        }

        public ClaimsPrincipal ResolveClientIdentity(string apiKey)
        {
            var u = UserManager.FindUserByApiKey(apiKey);
            if (u == null) return null;
            var id = new ClaimsPrincipal(new ClaimsIdentity(new GenericIdentity(u.FullName, "stateless"),
                new[]
                {
                    StatelessAuthClaim,
                    new Claim(UserApiKeyClaim, apiKey)
                }
            ));
            return id;
        }

        
    }
}