FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1

EXPOSE 8000

WORKDIR /chess_sa
COPY . /chess_sa

RUN sbt update clean compile

CMD sbt run
