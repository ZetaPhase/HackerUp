
using System.Text.RegularExpressions;
using HackerUp.Server.Configuration;
using HackerUp.Server.DataModels;
using HackerUp.Server.Services.Auth;
using Nancy;
using Nancy.ModelBinding;
using Nancy.Responses;
using Octokit;
using OsmiumSubstrate.Utilities;

namespace HackerUp.Server.Modules
{
    public class HUAuthenticationModule : NancyModule
    {
        public IHUServerContext ServerContext { get; set; }
        public HUAuthenticationModule(IHUServerContext serverContext) : base("/a")
        {
            ServerContext = serverContext;

            Post("/register", async args => 
            {
                try
                {                
                    var registration = this.Bind<RegistrationRequest>();

                    // Validate registration
                    if (registration.FullName == null)
                    {
                        return new TextResponse("Invalid name")
                            .WithStatusCode(HttpStatusCode.BadRequest);
                    }
                    if (registration.GHAuthToken == null)
                    {
                        return new TextResponse("Invalid auth token")
                            .WithStatusCode(HttpStatusCode.BadRequest);
                    }
                    // email: 
                    if (!Regex.IsMatch(registration.HangoutsEmail, @"\A(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?)\Z", RegexOptions.IgnoreCase))
                    {
                        return new TextResponse("Invalid email")
                            .WithStatusCode(HttpStatusCode.BadRequest);
                    }
                    // test the auth token to make sure it works
                    try
                    {               
                        var ghClient = new GitHubClient(new ProductHeaderValue(nameof(HUAuthenticationModule)));
                        ghClient.Credentials = new Credentials(registration.GHAuthToken);
                        var ghUser = await ghClient.User.Current();
                        var um = new UserManagerService(ServerContext);
                        var registeredUser = await um.RegisterUserAsync(registration, ghUser.Name);
                        if (registeredUser != null)
                        {
                            return Response.AsJsonNet(registeredUser);
                        }
                    }
                    catch
                    {
                        return HttpStatusCode.BadRequest;
                    }
                    return HttpStatusCode.Unauthorized;
                }
                catch
                {
                    return HttpStatusCode.BadRequest;
                }
            });
        }
    }
}