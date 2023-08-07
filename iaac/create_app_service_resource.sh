export RG_NAME=fowlart_app_service_rg
export SERVICE_PLAN_NAME=fowlart_app_service_plan
export ACR_NAME=fowlartcontainerregistry

export PETSTORE_APP_NAME=fowlart-pet-store
export PETSTORE_APP_SLOT_NAME=stage
export ORDER_SERVICE_APP_NAME=fowlart-order-service
export PET_SERVICE_APP_NAME=fowlart-pet-service
export PRODUCT_SERVICE_APP_NAME=fowlart-product-service

export RG_NAME_2=fowlart_app_service_rg_2
export SERVICE_PLAN_NAME_2=fowlart_app_service_plan_2
export mytrafficmanagerprofile=fowlart-pet-store
export PETSTORE_APP_NAME_2=fowlart-pet-store-2

# common
az group create -l polandcentral -n $RG_NAME
az group create -l northeurope -n $RG_NAME_2
az acr create -n $ACR_NAME --sku basic --admin-enabled true -g $RG_NAME

#first webb app

az appservice plan create -n $SERVICE_PLAN_NAME  -g $RG_NAME --sku S1 --output json --is-linux

az webapp create --runtime "JAVA:17-java17" -g $RG_NAME --public-network-access Enabled -p $SERVICE_PLAN_NAME -n $PETSTORE_APP_NAME
az webapp create --runtime "JAVA:17-java17" -g $RG_NAME --public-network-access Enabled -p $SERVICE_PLAN_NAME -n $ORDER_SERVICE_APP_NAME
az webapp create --runtime "JAVA:17-java17" -g $RG_NAME --public-network-access Enabled -p $SERVICE_PLAN_NAME -n $PET_SERVICE_APP_NAME
az webapp create --runtime "JAVA:17-java17" -g $RG_NAME --public-network-access Enabled -p $SERVICE_PLAN_NAME -n $PRODUCT_SERVICE_APP_NAME

# create deployment slot for petstore app
az webapp deployment slot create -g $RG_NAME -n $PETSTORE_APP_NAME -s $PETSTORE_APP_SLOT_NAME

#second webb app in second resource group(front-end ONLY)
az appservice plan create -n $SERVICE_PLAN_NAME_2  -g $RG_NAME_2 --sku S1 --output json --is-linux

az webapp create --runtime "JAVA:17-java17" -g $RG_NAME_2 --public-network-access Enabled -p $SERVICE_PLAN_NAME_2 -n $PETSTORE_APP_NAME_2

#traffic manager
az network traffic-manager profile create \
	--name $mytrafficmanagerprofile \
	--resource-group $RG_NAME \
	--routing-method Performance \
	--path '/' \
	--protocol "HTTP" \
	--unique-dns-name $mytrafficmanagerprofile  \
	--ttl 30 \
  --port 80

App1ResourceId=$(az webapp show --name $PETSTORE_APP_NAME --resource-group $RG_NAME --query id --output tsv)

az network traffic-manager endpoint create \
    --name $PETSTORE_APP_NAME \
    --resource-group $RG_NAME \
    --profile-name $mytrafficmanagerprofile \
    --type azureEndpoints \
    --target-resource-id $App1ResourceId \
    --endpoint-status Enabled

App2ResourceId=$(az webapp show --name $PETSTORE_APP_NAME_2 --resource-group $RG_NAME_2 --query id --output tsv)

az network traffic-manager endpoint create \
    --name $PETSTORE_APP_NAME_2 \
    --resource-group $RG_NAME \
    --profile-name $mytrafficmanagerprofile \
    --type azureEndpoints \
    --target-resource-id $App2ResourceId \
    --endpoint-status Enabled



