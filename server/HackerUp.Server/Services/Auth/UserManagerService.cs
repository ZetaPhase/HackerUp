
using System.Threading.Tasks;
using HackerUp.Server.Configuration;
using HackerUp.Server.DataModels;
using LiteDB;

namespace HackerUp.Server.Services.Auth
{
    public class UserManagerService
    {

        public IHUServerContext ServerContext { get; }
        public LiteCollection<RegisteredUser> UserStore { get; }

        public const string UserStoreDataKey = "users";
        public UserManagerService(IHUServerContext serverContext)
        {
            ServerContext = serverContext;
            UserStore = ServerContext.Database.GetCollection<RegisteredUser>(UserStoreDataKey);
        }

        public Task<RegisteredUser> RegisterUserAsync(RegistrationRequest request)
        {
            // Make sure no other users with a matching email exist
            if (UserStore.FindOne(x => x.HangoutsEmail == request.HangoutsEmail) == null)
            {
                // no conflicting users
                var newUser = new RegisteredUser(request.FullName, request.HangoutsEmail, request.GHAuthToken);
                // upsert
                UserStore.Upsert(newUser);
                return Task.FromResult(newUser);
            }
            else
            {
                return Task.FromResult<RegisteredUser>(null);
            }
        }
    }
}