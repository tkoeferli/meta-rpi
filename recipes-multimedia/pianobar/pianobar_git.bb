DESCRIPTION = "Console-based pandora.com player"
AUTHOR = "Lars-Dominik Braun <lars@6xq.net>"
HOMEPAGE = "https://6xq.net/pianobar/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=cfeb8ae0065c00f1bf4f5a963872e934"

DEPENDS = "curl faad2 ffmpeg gnutls json-c libao libgcrypt"

inherit pkgconfig

SRCREV = "88b3fded598a16d52211b0dec442adc2810b2c6b"
SRC_URI = " \
    git://github.com/PromyLOPh/pianobar;protocol=git \
    file://config \
"

S = "${WORKDIR}/git"

do_compile () {
    oe_runmake 'PREFIX=${D}${prefix}' 'DISABLE_MAD=1'
}

do_install () {
    oe_runmake 'PREFIX=${D}${prefix}' 'DISABLE_MAD=1' install

    install -d ${D}${datadir}/pianobar
    install -m 0664 ${WORKDIR}/config ${D}${datadir}/pianobar/config 
}

pkg_postinst:${PN}() {
    if [ ! -d $D/home/root/.config/pianobar ]; then
        mkdir -p $D/home/root/.config/pianobar
        cp $D/${datadir}/pianobar/config $D/home/root/.config/pianobar/config
    fi
}

RDEPENDS:${PN} += "libao-plugin-libalsa libavfilter"

FILES:${PN} = "${bindir} ${datadir}"
