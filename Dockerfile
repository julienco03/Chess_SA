FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1

COPY ./target /app/src

EXPOSE 8000

CMD [ "sbt", "run" ]
