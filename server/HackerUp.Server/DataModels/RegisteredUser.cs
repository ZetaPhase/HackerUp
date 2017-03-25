
namespace HackerUp.Server.DataModels
{
    public class RegisteredUser : DatabaseObject
    {
        public RegisteredUser(string fullName, string hangoutsEmail, string ghToken)
        {
            FullName = fullName;
            HangoutsEmail = hangoutsEmail;
            GHAuthToken = ghToken;
        }
        public string FullName { get; }
        public string HangoutsEmail { get; }
        public string GHAuthToken { get; }

        public string ApiKey { get; }
    }
}