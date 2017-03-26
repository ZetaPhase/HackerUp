
namespace HackerUp.Server.DataModels
{
    public class UserProfile
    {
        public string FullName { get; set; }
        public string HangoutsEmail { get; set; }
        public string GitHubBio { get; set; }
        public string GitHubUsername { get; set; }

        public string Company { get; set; }
        public string HomeLocation { get; set; }
        public int RepoCount { get; set; }

    }
}