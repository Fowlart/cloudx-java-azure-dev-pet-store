export RG_NAME=fowlart_app_service_rg
export SERVICE_PLAN_NAME=fowlart_app_service_plan
export ACR_NAME=fowlartcontainerregistry

export PETSTORE_APP_NAME=fowlart-pet-store
export ORDER_SERVICE_APP_NAME=fowlart-order-service
export PET_SERVICE_APP_NAME=fowlart-pet-service
export PRODUCT_SERVICE_APP_NAME=fowlart-product-service

# common
az group create -l polandcentral -n $RG_NAME
az acr create -n $ACR_NAME --sku basic --admin-enabled true -g $RG_NAME

#first webb app

az appservice plan create -n $SERVICE_PLAN_NAME  -g $RG_NAME --sku S1 --output json --is-linux

az webapp create --runtime "JAVA:17-java17" -g $RG_NAME --public-network-access Enabled -p $SERVICE_PLAN_NAME -n $PETSTORE_APP_NAME
#az webapp create --runtime "JAVA:17-java17" -g $RG_NAME --public-network-access Enabled -p $SERVICE_PLAN_NAME -n $ORDER_SERVICE_APP_NAME
#az webapp create --runtime "JAVA:17-java17" -g $RG_NAME --public-network-access Enabled -p $SERVICE_PLAN_NAME -n $PET_SERVICE_APP_NAME
#az webapp create --runtime "JAVA:17-java17" -g $RG_NAME --public-network-access Enabled -p $SERVICE_PLAN_NAME -n $PRODUCT_SERVICE_APP_NAME



