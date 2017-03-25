
using LiteDB;
using Newtonsoft.Json;

namespace HackerUp.Server.DataModels
{
    public class DatabaseObject
    {
        [JsonIgnore]
        [BsonId]
        public int DatabaseId { get; set; }
    }
}