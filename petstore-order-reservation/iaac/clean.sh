export RG_NAME=fowlartorderreserver-rg
export RG_NAME_APP=fowlart_app_service_rg

az group delete --name $RG_NAME --yes
az group delete --name $RG_NAME_APP --yes