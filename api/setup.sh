#!/bin/bash

set -eou pipefail

. env.properties

# install cert
mkcert -p12-file ./src/main/resources/local.p12 -pkcs12 ${LOCAL_DOMAIN} localhost

# set up to resolve urls
sudo -- sh -c -e "echo '127.0.0.1   ${LOCAL_DOMAIN}' >> /etc/hosts";
