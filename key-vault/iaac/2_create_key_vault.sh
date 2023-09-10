export RG_NAME=fowlart_app_service_rg
export KEY_VAULT_NAME=fowlart-key-vault

export PETSTORE_APP_NAME=fowlart-pet-store
export ORDER_SERVICE_APP_NAME=fowlart-order-service
export PET_SERVICE_APP_NAME=fowlart-pet-service
export PRODUCT_SERVICE_APP_NAME=fowlart-product-service

az keyvault create \
    --resource-group $RG_NAME \
    --location polandcentral \
    --name $KEY_VAULT_NAME

az webapp identity assign \
    --resource-group $RG_NAME \
    --name $PETSTORE_APP_NAME

az webapp identity assign \
    --resource-group $RG_NAME \
    --name $ORDER_SERVICE_APP_NAME

az webapp identity assign \
    --resource-group $RG_NAME \
    --name $PET_SERVICE_APP_NAME

az webapp identity assign \
    --resource-group $RG_NAME \
    --name $PRODUCT_SERVICE_APP_NAME

# list principalId of webapp
petstorePrincipalId=$(az webapp identity show \
                          --resource-group $RG_NAME \
                          --name $PETSTORE_APP_NAME \
                          --query principalId \
                          --output tsv)

orderServicePrincipalId=$(az webapp identity show \
                          --resource-group $RG_NAME \
                          --name $ORDER_SERVICE_APP_NAME \
                          --query principalId \
                          --output tsv)

petServicePrincipalId=$(az webapp identity show \
                          --resource-group $RG_NAME \
                          --name $PET_SERVICE_APP_NAME \
                          --query principalId \
                          --output tsv)

productServicePrincipalId=$(az webapp identity show \
                            --resource-group $RG_NAME \
                            --name $PRODUCT_SERVICE_APP_NAME \
                            --query principalId \
                            --output tsv)
az keyvault set-policy \
    --secret-permissions get list \
    --resource-group $RG_NAME \
    --name $KEY_VAULT_NAME \
    --object-id $petstorePrincipalId

az keyvault set-policy \
    --secret-permissions get list\
    --resource-group $RG_NAME \
    --name $KEY_VAULT_NAME \
    --object-id $orderServicePrincipalId

az keyvault set-policy \
    --secret-permissions get list \
    --resource-group $RG_NAME \
    --name $KEY_VAULT_NAME \
    --object-id $petServicePrincipalId

az keyvault set-policy \
    --secret-permissions get list \
    --resource-group $RG_NAME \
    --name $KEY_VAULT_NAME \
    --object-id $productServicePrincipalId