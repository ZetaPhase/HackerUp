
using OsmiumSubstrate.Configuration;
using HackerUp.Server.Configuration.Access;
using LiteDB;
using HackerUp.Server.Events;
using System.Collections.Generic;

namespace HackerUp.Server.Configuration
{
    public interface IHUServerContext : ISubstrateServerContext<HUAccessKey, HUApiAccessScope>
    {
        LiteDatabase Database { get; }
        HUEventLoop EventLoop { get; }

        List<ConnectedUser> ConnectedUsers { get; }
    }
}