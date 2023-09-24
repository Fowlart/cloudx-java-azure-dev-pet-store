export RG_NAME=fowlart_app_service_rg
export SERVICE_PLAN_NAME=fowlart_app_service_plan

export PETSTORE_APP_NAME=fowlart-pet-store
export ORDER_SERVICE_APP_NAME=fowlart-order-service
export PET_SERVICE_APP_NAME=fowlart-pet-service
export PRODUCT_SERVICE_APP_NAME=fowlart-product-service


az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings LOCATION=POLAND
az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings PETSTOREAPP_SERVER_PORT=80
az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings PETSTOREPRODUCTSERVICE_URL=https://fowlart-product-service.azurewebsites.net
az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings PETSTOREPETSERVICE_URL=https://fowlart-pet-service.azurewebsites.net
az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings PETSTOREORDERSERVICE_URL=https://fowlart-order-service.azurewebsites.net
az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings PETSTOREAPP_B2C_CREDENTIAL_CLIENT_ID="4a25d6da-1afa-420c-89ab-53331de7ea4e"
az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings PETSTOREAPP_B2C_CREDENTIAL_CLIENT_SECRET="SmT8Q~OAA1RQugQntG3.QTCRtkBCa9TfSd5CkbkT"
az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings PETSTOREAPP_B2C_CREDENTIAL_REDIRECT_URI=https://fowlart-pet-store.azurewebsites.net/login


#az webapp config appsettings set --name $PRODUCT_SERVICE_APP_NAME --resource-group $RG_NAME --settings PETSTOREPRODUCTSERVICE_SERVER_PORT=80
#az webapp config appsettings set --name $PET_SERVICE_APP_NAME --resource-group $RG_NAME --settings PETSTOREPETSERVICE_SERVER_PORT=80
#az webapp config appsettings set --name $ORDER_SERVICE_APP_NAME --resource-group $RG_NAME --settings PETSTOREORDERSERVICE_SERVER_PORT=80
#az webapp config appsettings set --name $ORDER_SERVICE_APP_NAME --resource-group $RG_NAME --settings PETSTOREPRODUCTSERVICE_URL=https://fowlart-product-service.azurewebsites.net