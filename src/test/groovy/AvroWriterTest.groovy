import com.bigdata.avro.schema.*
import com.bigdata.avro.utils.AvroDataWorker
import org.apache.avro.Schema
import org.apache.commons.io.FileUtils
import spock.lang.Specification

import java.nio.file.Paths

import static com.bigdata.avro.utils.AvroDataWorker.*

class AvroWriterTest extends Specification {
    def output

    def cleanup() {
        FileUtils.deleteQuietly(output as File)
    }


    def "create Destination Avro record"() {

        given:
        String baseDir = getBaseDir()
        String input = baseDir + File.separator + "destinations.csv"
        output = File.createTempFile("csv-", ".avro")

        Schema schema = Destination.getClassSchema()


//        input = "/home/vq/Downloads/expedia-hotel-recommendations/destinations.csv"
//        output =  new File("/home/vq/hadoop/destination.avro")

        when:
        csvToAvro(input, output.getAbsolutePath(), schema, Destination.class)


        then:
        noExceptionThrown()
        avroToObj(output, Destination.class).size() == 9
        avroToObj(output, Destination.class).get(0).getSrchDestinationId() == 0
        avroToObj(output, Destination.class).get(1).getSrchDestinationId() == 1
        avroToObj(output, Destination.class).get(0).getD1().toString() == "-2.19865708695"
        avroToObj(output, Destination.class).get(1).getD1().toString() == "-2.18169033283"
        avroToObj(output, Destination.class).get(2).getD1().toString() == "-2.18348974514"
        avroToObj(output, Destination.class).get(3).getD1().toString() == "-2.17740922654"

    }


    def "create Test Avro record"() {

        given:
        String baseDir = getBaseDir()
        String input = baseDir + File.separator + "test.csv"
        output = File.createTempFile("csv-", ".avro")

//        input = "/home/vq/Downloads/expedia-hotel-recommendations/test.csv"
//        output =  new File("/home/vq/hadoop/test.avro")

        Schema schema = Test.getClassSchema()

        when:
        csvToAvro(input, output.getAbsolutePath(), schema, Test.class)


        then:
        noExceptionThrown()
        avroToObj(output, Test.class).size() == 3
        avroToObj(output, Test.class).get(index).get("date_time").toString() == date_time as String
        avroToObj(output, Test.class).get(index).get("site_name").toString() == site_name as String
        avroToObj(output, Test.class).get(index).get("posa_continent").toString() == posa_continent as String
        avroToObj(output, Test.class).get(index).get("user_location_country").toString() == user_location_country as String
        avroToObj(output, Test.class).get(index).get("user_location_region").toString() == user_location_region as String
        avroToObj(output, Test.class).get(index).get("user_location_city").toString() == user_location_city as String
        avroToObj(output, Test.class).get(index).get("orig_destination_distance").toString() == orig_destination_distance as String
        avroToObj(output, Test.class).get(index).get("user_id").toString() == user_id as String
        avroToObj(output, Test.class).get(index).get("is_mobile").toString() == is_mobile as String
        avroToObj(output, Test.class).get(index).get("is_package").toString() == is_package as String
        avroToObj(output, Test.class).get(index).get("channel").toString() == channel as String
        avroToObj(output, Test.class).get(index).get("srch_ci").toString() == srch_ci as String
        avroToObj(output, Test.class).get(index).get("srch_co").toString() == srch_co as String
        avroToObj(output, Test.class).get(index).get("srch_adults_cnt").toString() == srch_adults_cnt as String
        avroToObj(output, Test.class).get(index).get("srch_children_cnt").toString() == srch_children_cnt as String
        avroToObj(output, Test.class).get(index).get("srch_rm_cnt").toString() == srch_rm_cnt as String
        avroToObj(output, Test.class).get(index).get("srch_destination_id").toString() == srch_destination_id as String
        avroToObj(output, Test.class).get(index).get("srch_destination_type_id").toString() == srch_destination_type_id as String
        avroToObj(output, Test.class).get(index).get("hotel_continent").toString() == hotel_continent as String
        avroToObj(output, Test.class).get(index).get("hotel_country").toString() == hotel_country as String
        avroToObj(output, Test.class).get(index).get("hotel_market").toString() == hotel_market as String


        where:
        index | date_time             | site_name | posa_continent | user_location_country | user_location_region | user_location_city | orig_destination_distance | user_id | is_mobile | is_package | channel | srch_ci      | srch_co      | srch_adults_cnt | srch_children_cnt | srch_rm_cnt | srch_destination_id | srch_destination_type_id | hotel_continent | hotel_country | hotel_market
        0     | "2015-09-03 17:09:54" | 2         | 3              | 66                    | 174                  | 37449              | "5539.0567"               | 1       | 1         | 0          | 3       | "2016-05-19" | "2016-05-23" | 2               | 0                 | 1           | 12243               | 6                        | 6               | 204           | 27
        1     | "2015-08-10 13:35:02" | 11        | 3              | 214                   | 120                  | 44496              | ""                        | 56      | 0         | 0          | 10      | "2015-08-18" | "2015-08-21" | 2               | 1                 | 1           | 20813               | 6                        | 6               | 70            | 312

    }

    private def getBaseDir() {
        def res = getClass().getClassLoader().getResource("csv")
        def file = Paths.get(res.toURI()).toFile()
        file.getAbsolutePath()
    }

}
