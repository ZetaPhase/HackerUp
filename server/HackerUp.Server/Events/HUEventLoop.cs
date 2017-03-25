
using HackerUp.Server.Configuration;

namespace HackerUp.Server.Events
{
    public class HUEventLoop
    {
        public IHUServerContext ServerContext { get; }

        public HUEventLoop(IHUServerContext serverContext)
        {
            ServerContext = serverContext;
        }
    }
}