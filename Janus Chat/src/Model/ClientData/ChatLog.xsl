<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" indent="yes" />
	<xsl:template match="/">
		<xsl:for-each select="//coversation/time">
			<p>
				<font size=
					<xsl:value-of select="./preferences/@size" />
					face=
					<xsl:value-of select="./preferences/@font" />
					color=
					<xsl:value-of select="./preferences/@color" />
					>
					<xsl:value-of select="./message/sender" />
					:
					<xsl:value-of select="./message/text" />
				</font>
			</p>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
