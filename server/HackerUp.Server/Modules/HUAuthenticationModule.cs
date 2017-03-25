
using HackerUp.Server.Configuration;
using Nancy;

namespace HackerUp.Server.Modules
{
    public class HUAuthenticationModule : NancyModule
    {

        public IHUServerContext ServerContext { get; set; }
        public HUAuthenticationModule(IHUServerContext serverContext)
        {
            ServerContext = serverContext;

            Post("/register", args => 
            {
                

                return HttpStatusCode.Unauthorized;
            });
        }


    }
}