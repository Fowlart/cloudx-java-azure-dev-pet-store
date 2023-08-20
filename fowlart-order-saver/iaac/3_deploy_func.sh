export ORDER_SERVICE_APP_NAME=fowlart-order-service
export RG_NAME=fowlart_app_service_rg

cd //Users/artur/IdeaProjects/cloudx-java-azure-dev-pet-store/fowlart-order-saver || echo "Directory not found"
mvn clean package
mvn azure-functions:package
mvn azure-functions:deploy

# create storage account, get connection string and set it as app setting
az storage account create --name fowlartstorageaccount  --resource-group fowlartordersaver-rg --location northeurope --sku Standard_LRS

# get connection string to storage account
connectionString=$(az storage account show-connection-string --name fowlartstorageaccount --resource-group fowlartordersaver-rg --output tsv)

echo "Storage account connectionString is $connectionString"

# get url of deployed function
url=$(az functionapp show --name fowlartordersaver --resource-group fowlartordersaver-rg --output tsv --query "hostNames[0]")

# set connection string as app setting in function app
az webapp config appsettings set --name fowlartordersaver --resource-group fowlartordersaver-rg --settings BLOB_CONNECTION_STRING=$connectionString

# set url of deployed function as app setting in order service
az webapp config appsettings set --name $ORDER_SERVICE_APP_NAME --resource-group $RG_NAME --settings ORDER_SAVER_URL="https://$url/api/save_order"


