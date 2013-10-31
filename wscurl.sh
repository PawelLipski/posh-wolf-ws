curl \
--header "content-type: text/xml; charset=utf-8" \
--data @$1 \
http://localhost:8080/wstest
echo
