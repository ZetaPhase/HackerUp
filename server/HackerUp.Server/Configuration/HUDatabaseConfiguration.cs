using Newtonsoft.Json;

namespace HackerUp.Server.Configuration
{
    public class NADatabaseConfiguration
    {
        [JsonProperty("fileName")]
        public string FileName { get; internal set; } = "ii-analytics.lidb";
    }
}