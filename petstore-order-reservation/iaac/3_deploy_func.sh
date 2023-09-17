cd /Users/artur/IdeaProjects/cloudx-java-azure-dev-pet-store/petstore-order-reservation || echo "Directory not found"
mvn clean package
mvn azure-functions:package
mvn azure-functions:deploy

# create storage account, get connection string and set it as app setting
az storage account create --name fowlartstorageaccount  --resource-group fowlartorderreserver-rg --location northeurope --sku Standard_LRS

# get connection string to storage account
connectionString=$(az storage account show-connection-string --name fowlartstorageaccount --resource-group fowlartorderreserver-rg --output tsv)

echo "Storage account connectionString is $connectionString"

# set connection string as app setting in function app
az webapp config appsettings set --name OrderReservationFunction --resource-group fowlartorderreserver-rg --settings BLOB_CONNECTION_STRING=$connectionString



