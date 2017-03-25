
namespace HackerUp.Server.DataModels
{
    public class RegistrationRequest : DatabaseObject
    {
        public string FullName { get; set; }
        public string HangoutsEmail { get; set; }
        public string GHAuthToken { get; set; }
    }
}