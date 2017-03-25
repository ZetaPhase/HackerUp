
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

        public RegisteredUser()
        {
            
        }
        public string FullName { get; set; }
        public string HangoutsEmail { get; set; }
        public string GHAuthToken { get; set; }

        public string ApiKey { get; set; }

        public string PublicUserId { get; set; }
    }
}