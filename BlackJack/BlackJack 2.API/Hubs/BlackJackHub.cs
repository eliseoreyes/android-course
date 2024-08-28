using Microsoft.AspNetCore.SignalR;

namespace BlackJack.API.Hubs
{
    public class BlackJackHub : Hub
    {
        public async Task SendMessage(string user, string message)
        {
            await Clients.All.SendAsync("ReceiveMessage", user, message);
        }
    }
}
