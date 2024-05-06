FROM busybox

WORKDIR /app

COPY ./target /app

EXPOSE 8000

CMD tail -f /dev/null
