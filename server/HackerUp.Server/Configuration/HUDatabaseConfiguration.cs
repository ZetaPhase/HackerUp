using Newtonsoft.Json;

namespace HackerUp.Server.Configuration
{
    public class HUDatabaseConfiguration
    {
        [JsonProperty("fileName")]
        public string FileName { get; internal set; } = "hu_server.lidb";
    }
}