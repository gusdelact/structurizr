
FROM frekele/gradle

RUN apt-get update && apt-get -y install git \
                                         vim

WORKDIR /usr

RUN git clone https://github.com/miguelvadillopro/structurizr.git

WORKDIR /usr/structurizr
