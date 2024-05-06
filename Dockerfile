FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1

WORKDIR /app

COPY ./target /app

EXPOSE 8000

CMD [ "sbt", "run" ]
