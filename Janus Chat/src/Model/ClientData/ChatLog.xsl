<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" indent="yes" />
	<xsl:template match="/">
		<xsl:for-each select="//conversation/time">
			<p>
				<xsl:attribute-set name="font">
					<xsl:attribute name="fname"><xsl:value-of
						select="./preferences/@size" /></xsl:attribute>
					<xsl:attribute name="size"><xsl:value-of
						select="./preferences/@font" /></xsl:attribute>
					<xsl:attribute name="color"><xsl:value-of
						select="./preferences/@color" /></xsl:attribute>
				</xsl:attribute-set>
				<xsl:value-of select="./message/@sender" />
				:
				<xsl:value-of select="./message/@text" />
			</p>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
