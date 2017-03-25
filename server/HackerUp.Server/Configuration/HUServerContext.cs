
namespace HackerUp.Server.Configuration
{
    public class HUServerContext : IHUServerContext
    {
        private HUServerParameters serverParameters;

        public HUServerContext(HUServerParameters serverParameters)
        {
            this.serverParameters = serverParameters;
        }
    }
}