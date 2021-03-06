<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"   xmlns:gmd="http://www.isotc211.org/2005/gmd" xmlns:gco="http://www.isotc211.org/2005/gco" xmlns:srv="http://www.isotc211.org/2005/srv" xmlns:xlink="http://www.w3.org/1999/xlink">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="no"/>
	<xsl:template match="/">
identifier=<xsl:value-of select="/gmd:MD_Metadata/gmd:fileIdentifier/gco:CharacterString"/>
title=<xsl:value-of select="/gmd:MD_Metadata/gmd:identificationInfo/srv:SV_ServiceIdentification/gmd:citation/gmd:CI_Citation/gmd:title/gco:CharacterString"/>
description=<xsl:value-of select="/gmd:MD_Metadata/gmd:identificationInfo/srv:SV_ServiceIdentification/gmd:abstract/gco:CharacterString"/>
resource.url=<xsl:value-of select="/gmd:MD_Metadata/gmd:identificationInfo/srv:SV_ServiceIdentification/srv:containsOperations/srv:SV_OperationMetadata/srv:connectPoint/gmd:CI_OnlineResource/gmd:linkage/gmd:URL | /gmd:MD_Metadata/gmd:identificationInfo/srv:SV_ServiceIdentification/srv:operatesOn/@xlink:href"/>
bbox=<xsl:value-of select="/gmd:MD_Metadata/gmd:identificationInfo/srv:SV_ServiceIdentification/srv:extent/gmd:EX_Extent/gmd:geographicElement/gmd:EX_GeographicBoundingBox/gmd:westBoundLongitude/gco:Decimal"/><xsl:text> </xsl:text><xsl:value-of select="/gmd:MD_Metadata/gmd:identificationInfo/srv:SV_ServiceIdentification/srv:extent/gmd:EX_Extent/gmd:geographicElement/gmd:EX_GeographicBoundingBox/gmd:southBoundLatitude/gco:Decimal"/>,<xsl:value-of select="/gmd:MD_Metadata/gmd:identificationInfo/srv:SV_ServiceIdentification/srv:extent/gmd:EX_Extent/gmd:geographicElement/gmd:EX_GeographicBoundingBox/gmd:eastBoundLongitude/gco:Decimal"/><xsl:text> </xsl:text><xsl:value-of select="/gmd:MD_Metadata/gmd:identificationInfo/srv:SV_ServiceIdentification/srv:extent/gmd:EX_Extent/gmd:geographicElement/gmd:EX_GeographicBoundingBox/gmd:northBoundLatitude/gco:Decimal"/>
	</xsl:template>
</xsl:stylesheet>
