package com.impaladigital.photo_datasource_test

sealed class PhotoServiceResponseType{

    object EmptyList: PhotoServiceResponseType()

    object MalformedData: PhotoServiceResponseType()

    object GoodData: PhotoServiceResponseType()

    object Http404: PhotoServiceResponseType()

    object ValidPhotoData : PhotoServiceResponseType()
}
