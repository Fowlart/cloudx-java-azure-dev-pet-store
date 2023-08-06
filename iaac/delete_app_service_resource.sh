export RG_NAME=fowlart_app_service_rg
export RG_NAME_2=fowlart_app_service_rg_2

az group delete --name $RG_NAME --yes

az group delete --name $RG_NAME_2  --yes