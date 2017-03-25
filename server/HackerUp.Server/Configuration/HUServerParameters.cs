
using Newtonsoft.Json;
using HackerUp.Server.Configuration.Access;

namespace HackerUp.Server.Configuration
{
    public class HUServerParameters
    {
        [JsonProperty("databaseConfiguration")]
        public HUDatabaseConfiguration DatabaseConfiguration { get; set; }

        /// <summary>
        /// Master API keys. These will also be stored in the state but will not be duplicated.
        /// </summary>
        [JsonProperty("apikeys")]
        public HUAccessKey[] ApiKeys { get; set; } = new HUAccessKey[0];

        /// <summary>
        /// If set to true, the keys will be reset upon a server start.
        /// </summary>
        public bool KeyReset { get; set; } = false;

        [JsonProperty("corsOrigins")]
        public string[] CorsOrigins { get; set; } = new string[0];

        public string BaseDirectory { get; internal set; }
    }
}