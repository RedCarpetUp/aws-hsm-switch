# aws-hsm-switch

### Creation of keystore

1. Create SSL using let's encrypt
sudo certbot certonly --standalone
2. export to pkcs12
openssl pkcs12 -export -in /etc/letsencrypt/live/hsmpi.redcarpetup.com/cert.pem -inkey /etc/letsencrypt/live/hsmpi.redcarpetup.com/privkey.pem -out /home/ec2-user/redcpt.p12 -name hsmpi.redcarpetup.com -CAfile /etc/letsencrypt/live/hsmpi.redcarpetup.com/fullchain.pem -caname "Let's Encrypt Authority X3" -password pass:mykeystore
3. convert to keystore
keytool -importkeystore -deststorepass mykeystore -destkeypass mykeystore -deststoretype pkcs12 -srckeystore /home/ec2-user/redcpt.p12 -srcstoretype PKCS12 -srcstorepass mykeystore -destkeystore /home/ec2-user/redcpt.keystore -alias hsmpi.redcarpetup.com
