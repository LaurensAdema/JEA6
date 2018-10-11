FROM microsoft/dotnet:2.1-aspnetcore-runtime AS base
WORKDIR /app
EXPOSE 53419
EXPOSE 44376

FROM microsoft/dotnet:2.1-sdk AS build
WORKDIR /src
COPY ["Kwetter-Admin/Kwetter-Admin.csproj", "Kwetter-Admin/"]
RUN dotnet restore "Kwetter-Admin/Kwetter-Admin.csproj"
COPY . .
WORKDIR "/src/Kwetter-Admin"
RUN dotnet build "Kwetter-Admin.csproj" -c Release -o /app

FROM build AS publish
RUN dotnet publish "Kwetter-Admin.csproj" -c Release -o /app

FROM base AS final
WORKDIR /app
COPY --from=publish /app .
ENTRYPOINT ["dotnet", "Kwetter-Admin.dll"]