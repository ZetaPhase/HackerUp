
using OsmiumSubstrate.Configuration;
using HackerUp.Server.Configuration.Access;
using System;
using System.Collections.Generic;
using LiteDB;

namespace HackerUp.Server.Configuration
{
    public class HUServerState : ISubstrateServerState<HUAccessKey, HUApiAccessScope>
    {
        public List<HUAccessKey> ApiKeys { get; } = new List<HUAccessKey>();

        [BsonIgnore]
        IEnumerable<HUAccessKey> ISubstrateServerState<HUAccessKey, HUApiAccessScope>.ApiKeys => ApiKeys;

        [BsonId]
        public ObjectId DatabaseId { get; set; }

        [BsonIgnore]
        public LiteCollection<HUServerState> PersistenceMedium { get; set; }

        [BsonIgnore]
        public Action Persist { get; set; }
    }
}