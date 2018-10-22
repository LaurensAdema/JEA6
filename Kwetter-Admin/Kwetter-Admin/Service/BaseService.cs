using ma.ade.Kwetter2.Admin.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using Hanssens.Net;
using ma.ade.Kwetter2.Admin.Extensions;
using ma.ade.Kwetter2.Admin.Interfaces;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;

namespace ma.ade.Kwetter2.Admin.Service
{
    public abstract class BaseService<T>: IBaseService<T>
    {
        private readonly HttpClient _httpClient;
        protected readonly IConfiguration _configuration;
        protected readonly IHttpContextAccessor _httpContextAccessor;
        private readonly Uri _baseEndpoint;

        protected Token Token
        {
            get
            {
                if (_httpContextAccessor?.HttpContext?.Session != null && _httpContextAccessor.HttpContext.Session.TryGetValue("token", out byte[] tokenBytes) && tokenBytes != null)
                {
                    return tokenBytes.ToObject<Token>();
                }

                return null;
            }
        }

        protected BaseService(IConfiguration configuration, IHttpContextAccessor httpContextAccessor, Uri baseEndpoint)
        {
            _configuration = configuration;
            _httpContextAccessor = httpContextAccessor;
            _baseEndpoint = baseEndpoint ?? throw new ArgumentNullException(nameof(baseEndpoint));
            _httpClient = new HttpClient();
        }

        private async Task<TR> RequestExecuter<TR>(Task<HttpResponseMessage> responseTask)
        {
            AddHeaders();
            HttpResponseMessage response = await responseTask;
            response.EnsureSuccessStatusCode();
            string data = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<TR>(data);
        }

        private async Task RequestExecuter(Task<HttpResponseMessage> responseTask)
        {
            AddHeaders();
            HttpResponseMessage response = await responseTask;
            response.EnsureSuccessStatusCode();
        }

        protected async Task<TR> GetAsync<TR>(Uri requestUrl) => await RequestExecuter<TR>(_httpClient.GetAsync(requestUrl, HttpCompletionOption.ResponseHeadersRead));

        protected async Task<TR> DeleteAsync<TR>(Uri requestUrl) => await RequestExecuter<TR>(_httpClient.DeleteAsync(requestUrl));

        protected async Task DeleteAsync(Uri requestUrl) => await RequestExecuter(_httpClient.DeleteAsync(requestUrl));

        protected async Task<TR> PostAsync<TR, TP>(Uri requestUrl, TP content) => await RequestExecuter<TR>(_httpClient.PostAsync(requestUrl.ToString(), CreateHttpContent(content)));

        protected async Task PostAsync<TP>(Uri requestUrl, TP content) => await RequestExecuter(_httpClient.PostAsync(requestUrl.ToString(), CreateHttpContent(content)));

        protected async Task<TR> PutAsync<TR>(Uri requestUrl, TR content) => await RequestExecuter<TR>(_httpClient.PutAsync(requestUrl.ToString(), CreateHttpContent(content)));

        protected async Task PutAsyncVoid<TP>(Uri requestUrl, TP content) => await RequestExecuter(_httpClient.PutAsync(requestUrl.ToString(), CreateHttpContent(content)));

        protected async Task<TR> PatchAsync<TR>(Uri requestUrl, TR content) => await RequestExecuter<TR>(_httpClient.PatchAsync(requestUrl.ToString(), CreateHttpContent(content)));

        protected async Task PatchAsyncVoid<TP>(Uri requestUrl, TP content) => await RequestExecuter(_httpClient.PatchAsync(requestUrl.ToString(), CreateHttpContent(content)));

        protected Uri CreateRequestUri(string relativePath, string queryString = "")
        {
            UriBuilder uriBuilder = new UriBuilder(new Uri(_baseEndpoint.AbsoluteUri + relativePath))
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
            DateFormatHandling = DateFormatHandling.IsoDateFormat,
            NullValueHandling = NullValueHandling.Ignore
        };

        private void AddHeaders()
        {
            if (Token != null)
            {
                _httpClient.DefaultRequestHeaders.Remove("Authorization");
                _httpClient.DefaultRequestHeaders.Add("Authorization", $"Bearer {Token.AccessToken}");
            }
        }

        public virtual async Task<T> GetAsync(long id) => await GetAsync<T>(CreateRequestUri($"/{id}"));

        public virtual async Task<IEnumerable<T>> GetAllAsync() => await GetAsync<IEnumerable<T>>(CreateRequestUri(""));

        public virtual async Task<T> CreateAsync(T entity) => await PutAsync(CreateRequestUri(""), entity);

        public virtual async Task UpdateAsync(T entity) => await PatchAsyncVoid(CreateRequestUri(""), entity);

        public virtual async Task DeleteAsync(long id) => await DeleteAsync(CreateRequestUri($"/{id}"));
    }
}
