export RG_NAME=fowlart_app_service_rg
export SERVICE_PLAN_NAME=fowlart_app_service_plan

export PETSTORE_APP_NAME=fowlart-pet-store
export ORDER_SERVICE_APP_NAME=fowlart-order-service
export PET_SERVICE_APP_NAME=fowlart-pet-service
export PRODUCT_SERVICE_APP_NAME=fowlart-product-service
export KEY_VAULT_NAME=fowlart-key-vault


az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings LOCATION=POLAND
az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings PETSTOREAPP_SERVER_PORT=80
az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings PETSTOREPRODUCTSERVICE_URL=https://fowlart-product-service.azurewebsites.net
az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings PETSTOREPETSERVICE_URL=https://fowlart-pet-service.azurewebsites.net
az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings PETSTOREORDERSERVICE_URL=https://fowlart-order-service.azurewebsites.net
az webapp config appsettings set --name $PETSTORE_APP_NAME --resource-group $RG_NAME --settings KEY_VAULT_NAME=$KEY_VAULT_NAME



az webapp config appsettings set --name $PRODUCT_SERVICE_APP_NAME --resource-group $RG_NAME --settings PETSTOREPRODUCTSERVICE_SERVER_PORT=80
az webapp config appsettings set --name $PRODUCT_SERVICE_APP_NAME --resource-group $RG_NAME --settings KEY_VAULT_NAME=$KEY_VAULT_NAME

az webapp config appsettings set --name $PET_SERVICE_APP_NAME --resource-group $RG_NAME --settings PETSTOREPETSERVICE_SERVER_PORT=80
az webapp config appsettings set --name $PET_SERVICE_APP_NAME --resource-group $RG_NAME --settings KEY_VAULT_NAME=$KEY_VAULT_NAME

az webapp config appsettings set --name $ORDER_SERVICE_APP_NAME --resource-group $RG_NAME --settings PETSTOREORDERSERVICE_SERVER_PORT=80
az webapp config appsettings set --name $ORDER_SERVICE_APP_NAME --resource-group $RG_NAME --settings KEY_VAULT_NAME=$KEY_VAULT_NAME
az webapp config appsettings set --name $ORDER_SERVICE_APP_NAME --resource-group $RG_NAME --settings PETSTOREPRODUCTSERVICE_URL=https://fowlart-product-service.azurewebsites.net

