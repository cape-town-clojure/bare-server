(ns bare-server.db
  (:require [datomic.api :as d]))

(def uri "datomic:mem://sandbox")

(defn conn []
  (d/connect uri))

(defn db []
  (d/db (conn)))

(defn bootstrap! []
  (d/create-database uri)
  (d/transact (conn)
              [{:db/id                 (d/tempid :db.part/db)
                :db/ident              :person/name
                :db/valueType          :db.type/string
                :db/cardinality        :db.cardinality/one
                :db/doc                "A person's name"
                :db.install/_attribute :db.part/db}
               {:db/id                 (d/tempid :db.part/db)
                :db/ident              :person/age
                :db/valueType          :db.type/string
                :db/cardinality        :db.cardinality/one
                :db/doc                "A person's age"
                :db.install/_attribute :db.part/db}])
  (d/transact (conn)
              [{:db/id       (d/tempid :db.part/user)
                :person/name "Antoine"
                :person/age  "23"}
               {:db/id       (d/tempid :db.part/user)
                :person/name "Pierre"}
               {:db/id       (d/tempid :db.part/user)
                :person/name "Lucien"}
               {:db/id       (d/tempid :db.part/user)
                :person/name "Robert"}]))

(comment

  (d/delete-database uri)

  (bootstrap!)

  (d/q '[:find ?p .
         :in $ [?name ...]
         :where
         [?p :person/name ?name]
         [?p :person/age ?age]
         [(re-find #"Ant" ?name)]
         [(bare-server.db/nickname ?name) ?nick]]
       (db) ["Antoine" "Pierre"])

  (:person/name (d/entity (db) 17592186045418))

  (d/pull (db) [:person/name] 17592186045418)


  )
