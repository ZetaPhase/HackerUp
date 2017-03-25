
using HackerUp.Server.Configuration;
using HackerUp.Server.Configuration.Access;
using Nancy;
using Nancy.Authentication.Stateless;
using Nancy.Bootstrapper;
using Nancy.Session;
using Nancy.TinyIoc;
using OsmiumSubstrate.Services.Authentication;

namespace HackerUp.Server
{
    public class HUBootstrapper : DefaultNancyBootstrapper
    {
        public HUServerContext ServerContext { get; set; }

        public HUBootstrapper(HUServerContext serverContext)
        {
            ServerContext = serverContext;
        }

        protected override void ApplicationStartup(TinyIoCContainer container, IPipelines pipelines)
        {
            base.ApplicationStartup(container, pipelines);

            // Connect to database
            ServerContext.ConnectDatabase();

            // TODO (Disabled): Load plugins

            // Enable cookie sessions
            CookieBasedSessions.Enable(pipelines);

            // Enable authentication
            StatelessAuthentication.Enable(pipelines, new StatelessAuthenticationConfiguration(ctx =>
            {
                // Take API from query string
                var apiKey = (string)ctx.Request.Query.apikey.Value;

                // get user identity
                var authenticator = new StatelessAuthenticationService<HUAccessKey, HUApiAccessScope>(ServerContext);
                return authenticator.ResolveClientIdentity(apiKey);
            }));

            // Enable CORS
            pipelines.AfterRequest.AddItemToEndOfPipeline((ctx) =>
            {
                foreach (var origin in ServerContext.Parameters.CorsOrigins)
                {
                    ctx.Response.WithHeader("Access-Control-Allow-Origin", origin);
                }
                ctx.Response
                    .WithHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE")
                    .WithHeader("Access-Control-Allow-Headers", "Accept, Origin, Content-type");
            });
        }

        protected override void ConfigureApplicationContainer(TinyIoCContainer container)
        {
            base.ConfigureApplicationContainer(container);

            // Register IoC components
            container.Register<IHUServerContext>(ServerContext);
        }
    }
}