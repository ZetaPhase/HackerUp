
using OsmiumSubstrate.Configuration;
using HackerUp.Server.Configuration.Access;
using LiteDB;

namespace HackerUp.Server.Configuration
{
    public interface IHUServerContext : ISubstrateServerContext<HUAccessKey, HUApiAccessScope>
    {
        LiteDatabase Database { get; }
    }
}