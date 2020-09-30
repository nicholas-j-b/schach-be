openapi-generator-cli generate \
-i https://raw.githubusercontent.com/nicholas-j-b/schachfish-api/schach-be/src/main/resources/openapi.yaml \
-g kotlin \
--model-package com.nicholasbrooking.pkg.schachbe.api.models \
--global-property models


# -i /home/nick/ws-web/games/schach/schachfish-api/src/main/resources/openapi.yaml \
