using ma.ade.Kwetter2.Admin.Models;
using Newtonsoft.Json;
using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace ma.ade.Kwetter2.Admin.Service
{
    public abstract class BaseService<T>
    {
        private readonly HttpClient _httpClient;
        private Uri BaseEndpoint { get; set; }
        private Token Token { get; set; }

        protected BaseService(Uri baseEndpoint, Token token)
        {
            BaseEndpoint = baseEndpoint ?? throw new ArgumentNullException(nameof(baseEndpoint));
            _httpClient = new HttpClient();
            Token = token;
        }

        /// <summary>  
        /// Common method for making GET calls  
        /// </summary>  
        protected async Task<T> GetAsync(Uri requestUrl)
        {
            AddHeaders();
            HttpResponseMessage response = await _httpClient.GetAsync(requestUrl, HttpCompletionOption.ResponseHeadersRead);
            response.EnsureSuccessStatusCode();
            string data = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<T>(data);
        }

        /// <summary>  
        /// Common method for making POST calls  
        /// </summary>  
        protected async Task<T> PostAsync(Uri requestUrl, T content)
        {
            AddHeaders();
            HttpResponseMessage response = await _httpClient.PostAsync(requestUrl.ToString(), CreateHttpContent(content));
            response.EnsureSuccessStatusCode();
            string data = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<T>(data);
        }
        protected async Task<T> PostAsync<T2>(Uri requestUrl, T2 content)
        {
            AddHeaders();
            HttpResponseMessage response = await _httpClient.PostAsync(requestUrl.ToString(), CreateHttpContent(content));
            response.EnsureSuccessStatusCode();
            string data = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<T>(data);
        }

        protected Uri CreateRequestUri(string relativePath, string queryString = "")
        {
            Uri endpoint = new Uri(BaseEndpoint, relativePath);
            UriBuilder uriBuilder = new UriBuilder(endpoint)
            {
                Query = queryString
            };
            return uriBuilder.Uri;
        }

        private HttpContent CreateHttpContent<TH>(TH content)
        {
            string json = JsonConvert.SerializeObject(content, MicrosoftDateFormatSettings);
            return new StringContent(json, Encoding.UTF8, "application/json");
        }

        private static JsonSerializerSettings MicrosoftDateFormatSettings => new JsonSerializerSettings
        {
            DateFormatHandling = DateFormatHandling.MicrosoftDateFormat
        };

        private void AddHeaders()
        {
            _httpClient.DefaultRequestHeaders.Remove("Authorization");
            _httpClient.DefaultRequestHeaders.Add("Authorization", $"Bearer {Token.AccessToken}");
        }
    }
}
