# Build Base Image
FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1

# Add Workdir
WORKDIR /Chess
ADD . /Chess

# Install Dependencys
RUN apt-get update && apt-get install -y locales && rm -rf /var/lib/apt/lists/*\
	&& localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8

RUN apt-get update && apt-get install -y libxrender1 libxtst6 libxi6
RUN apt-get install apt-transport-https curl gnupg -yqq
RUN apt install -y xauth

# Set Locale
ENV LANG en_DE.utf8

# Update sbt and start Game
RUN sbt update
CMD sbt run