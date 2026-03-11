FROM ubuntu:20.04
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update && apt-get install -y build-essential autoconf automake libtool pkg-config make libgmp3-dev libntl-dev clang git llvm libclang-dev nano vim mc
RUN mkdir /aoo
RUN cd /aoo && git clone git://repo.or.cz/barvinok.git
RUN cd /aoo/barvinok && ./get_submodules.sh && ./autogen.sh && ./configure --without-glpk --with-pet=bundled
RUN make -C /aoo/barvinok && make -C /aoo/barvinok check && make -C /aoo/barvinok install
RUN ln -s /aoo/barvinok/isl/.libs/libisl.so.23 /lib/libisl.so.23
RUN ln -s /aoo/barvinok/polylib/.libs/libpolylibgmp.so.8 /lib/libpolylibgmp.so.8
RUN ln -s /aoo/barvinok/pet/.libs/libpet.so.10 /lib/libpet.so.10
RUN cd /aoo && git clone git://repo.or.cz/pet.git 
RUN cd /aoo/pet && ./get_submodules.sh && ./autogen.sh && ./configure
RUN make -C /aoo/pet && make -C /aoo/pet check
CMD /bin/bash
