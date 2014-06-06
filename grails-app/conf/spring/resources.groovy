import com.imperil.marshalling.CustomObjectMarshallers
import com.imperil.marshalling.json.AppUserMarshaller

// Place your Spring DSL code here
beans = {
  customObjectMarshallers( CustomObjectMarshallers ) {
    springSecurityService = ref("springSecurityService")
    marshallers = [
      new AppUserMarshaller()
    ]
  }
}
