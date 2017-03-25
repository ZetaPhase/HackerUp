
namespace HackerUp.Server.DataModels
{
    public class RegistrationRequest : DatabaseObject
    {
        public string FullName { get; }
        public string HangoutsEmail { get; }
        public string GHAuthToken { get; }
    }
}